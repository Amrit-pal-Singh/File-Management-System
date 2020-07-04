from rest_framework.views import APIView
from django.contrib.auth.models import User
from django.http import JsonResponse
from database_management.models import (
    File,
    User,
    Role,
)
from rest_framework.permissions import (
	IsAuthenticated,
	IsAuthenticatedOrReadOnly,
)
from .serializers import ( 
    AddUserSerializer,
    AddRoleSerializer,
    AddFileSerializer,
)
from rest_framework import viewsets, mixins, status, generics
from rest_framework.authentication import TokenAuthentication
from rest_framework.authentication import SessionAuthentication
from .permissions import IsAdminUser
from file_management_system import settings
from rest_framework.generics import (CreateAPIView, RetrieveUpdateAPIView, UpdateAPIView, DestroyAPIView, ListAPIView, RetrieveAPIView)
from django.contrib.auth.hashers import make_password
import json
from rest_framework.authtoken.views import ObtainAuthToken
from rest_framework.authtoken.models import Token
from rest_framework.response import Response
from django.shortcuts import get_object_or_404
from datetime import datetime



class CustomAuthToken(ObtainAuthToken):
    def post(self, request, *args, **kwargs):
        response = super(CustomAuthToken, self).post(request, *args, **kwargs)
        token = Token.objects.get(key=response.data['token'])
        user = get_object_or_404(User, email=token.user.email)
        
        first_name = user.first_name
        last_name = user.last_name
        roles_dict = {}
        index = 0
        for i in user.roles.all():
            roles_dict[index] ={'name': i.name, 'department': i.department} 
            index += 1

        return Response({'token': token.key,
                        'email': token.user.email,
                        'admin_permissions': token.user.is_staff,
                        'first_name': first_name,
                        'last_name': last_name,
                        'roles': roles_dict
        })
        

class AddUser(mixins.CreateModelMixin,
                viewsets.GenericViewSet):
    
    authentication_classes = (TokenAuthentication, )
    permission_classes = (IsAuthenticated, )
    serializer_class = AddUserSerializer
    queryset = User.objects.all()
    def create(self, request, *args, **kwargs):
        self.serializer = self.get_serializer(data=request.data)
        self.serializer.is_valid(raise_exception=True)
        email = self.serializer.validated_data['email']
        password = self.serializer.validated_data['password']
        # self.serializer.validated_data['password'] = make_password(self.serializer.validated_data['password'])
        # password = self.serializer.validated_data['password']
        
        # username, email, password
        try:
            user = User.objects.create_user(email, password)
            user.first_name = self.serializer.validated_data['first_name']
            user.last_name = self.serializer.validated_data['last_name']
            user.email = email
            user.save()
        except:
            return Response({'error': 'User cannot be created'}, status=status.HTTP_400_BAD_REQUEST)
        return Response(self.serializer.validated_data, status=status.HTTP_201_CREATED)



class CreateFile(mixins.CreateModelMixin,
                viewsets.GenericViewSet):
    authentication_classes = (TokenAuthentication, )
    permission_classes = (IsAuthenticated, )
    serializer_class = AddFileSerializer
    queryset = File.objects.all()
    def create(self, request, *args, **kwargs):
        self.serializer = self.get_serializer(data=request.data)
        self.serializer.is_valid(raise_exception=true)
        qr = self.serializer.validated_data['qr']
        name = self.serializer.validated_data['name']
        user = request.user
        time_generated = datetime.now()
        restarted = False
        path = File.set_path("")
        approved = False


        # how to add plan to send. 
        # try to add blank=true in model check if it is allowed
        file = File.objects.create(qr=qr,name=name,user=user,time_generated=time_generated,restarted=restarted,path=path,approved=approved)
        
        # i dont think this is required but still try it.
        file.save();
        return Response(self.serializer.validated_data, status=status.HTTP_201_CREATED)        





class ListUsers(APIView):
    authentication_classes = (TokenAuthentication, )
    permission_classes = (IsAdminUser, IsAuthenticated)
    queryset = User.objects.all()
    def get(self, request, *args, **kwargs):
        objects = User.objects.all()
        dictUsers = {}
        index = 1
        for obj in objects:
            roles_dict = {}
            i = 0
            for i in obj.roles.all():
                roles_dict[index] ={'name': i.name, 'department': i.department} 
                index += 1

            dictUsers[index] = {'name': str(obj.first_name+" "+obj.last_name),'email': str(obj.email), 'roles': roles_dict}
            index+=1
        print(dictUsers)
        
        dictUsersDump = json.dumps(dictUsers)
        jsonUser = json.loads(dictUsersDump)
        return Response(jsonUser, status=status.HTTP_200_OK)
        # return JsonResponse(dictUsers)

class AllRolesOfUser(APIView):
    authentication_classes = (TokenAuthentication, )
    permission_classes = (IsAuthenticated, )
    def get(self, request, *args, **kwargs):
        user_logined = request.user
        roles = user_logined.roles.all()
        dict_roles = {}
        index = 1
        for role in roles:
            dict_roles[role.name] = role.department       
        print(dict_roles)
        dictRolesDump = json.dumps(dict_roles)
        jsonUser = json.loads(dictRolesDump)
        return Response(jsonUser, status=status.HTTP_200_OK)


class ViewMyGeneratedFiles(APIView):
    authentication_classes = (TokenAuthentication, )
    permission_classes = (IsAuthenticated, )
    def get(self, request, *args, **kwargs):            
        user_logined = request.user
        files = Files.objects.filter(user=user_logined)        
        dict_files = {}
        index = 1
        for file in files:
            dict_files[index] = {'qr': file.qr,
                                'name':file.name,
                                'time_generated':file.time_generated,
                                'restarted':file.restarted,
                                'path':file.get_path(),
                                'plan_to_send':file.plan_to_send,
                                'approved':file.approved}    
            index += 1
        dictFilesDump = json.dumps(dict_files)
        jsonFiles = json.loads(dictFilesDump)
        return Response(jsonFiles, status=status.HTTP_200_OK)


class ViewMyApprovedDisapprovedFiles():
    pass



