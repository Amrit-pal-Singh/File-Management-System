from rest_framework.views import APIView
from django.contrib.auth.models import User
from django.http import JsonResponse
from database_management.models import (
    File,
    User,
    Role,
    AppUser,
)
from rest_framework.permissions import (
	IsAuthenticated,
	IsAuthenticatedOrReadOnly,
)
from .serializers import ( 
    AddUserSerializer,
    AddRoleSerializer,
    AddFileSerializer,
    ReceiveFileSerializer,
    ApproveDisapproveSerializer,
    PlanToSendSerializer,
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
        app_user = get_object_or_404(AppUser, user=user)
        if(app_user == None):
            return
        print("$$$$$$$$$$$$$$$$$$")
        first_name = user.first_name
        last_name = user.last_name
        roles_dict = {}
        index = 0
        for i in app_user.roles.all():
            roles_dict[index] ={'name': i.name, 'department': i.department} 
            index += 1

        return Response({'token': token.key,
                        'email': token.user.email,
                        'admin_permissions': token.user.is_staff,
                        'first_name': first_name,
                        'last_name': last_name,
                        'roles': roles_dict
        }, status=status.HTTP_200_OK)
        


class CreateFile(mixins.CreateModelMixin,
                viewsets.GenericViewSet):
    authentication_classes = (TokenAuthentication, )
    permission_classes = (IsAuthenticated, )
    serializer_class = AddFileSerializer
    queryset = File.objects.all()
    def create(self, request, *args, **kwargs):
        self.serializer = self.get_serializer(data=request.data)
        self.serializer.is_valid(raise_exception=True)
        qr = self.serializer.validated_data['qr']
        name = self.serializer.validated_data['name']
        user_logined = request.user
        app_user = get_object_or_404(AppUser, user=user_logined)
        time_generated = datetime.now()
        restarted = False
        path = ""
        approved = ""
        file = File.objects.create(qr=qr,name=name,user=app_user,time_generated=time_generated,restarted=restarted,path=path,approved=approved)
        return Response(self.serializer.validated_data, status=status.HTTP_201_CREATED)        



class RecieveFile(APIView):
    # add to the path.
    authentication_classes = (TokenAuthentication, )
    permission_classes = (IsAuthenticated, )

    def put(self, request, pk):
        selected_file = get_object_or_404(File, qr=pk)
        serializer = ReceiveFileSerializer(instance=selected_file, data=request.data, partial=True)
        if serializer.is_valid(raise_exception=True):
            file_saved = serializer.save()
        return Response(serializer.validated_data, status=status.HTTP_200_OK)

class ApproveDissaprove(APIView):
    # add to the path.
    authentication_classes = (TokenAuthentication, )
    permission_classes = (IsAuthenticated, )

    def put(self, request, pk):
        selected_file = get_object_or_404(File, qr=pk)
        serializer = ApproveDisapproveSerializer(instance=selected_file, data=request.data, partial=True)
        if serializer.is_valid(raise_exception=True):
            file_saved = serializer.save()
        return Response(serializer.validated_data, status=status.HTTP_200_OK)


class PlanToSend(APIView):
    authentication_classes = (TokenAuthentication, )
    permission_classes = (IsAuthenticated, )
    def put(self, request, pk):
        selected_file = get_object_or_404(File, qr=pk)
        serializer = PlanToSendSerializer(instance=selected_file, data=request.data, partial=True)
        if serializer.is_valid(raise_exception=True):
            file_saved = serializer.save()
        return Response(serializer.validated_data, status=status.HTTP_200_OK)



class ViewMyGeneratedFiles(APIView):
    authentication_classes = (TokenAuthentication, )
    permission_classes = (IsAuthenticated, )
    def get(self, request, *args, **kwargs):            
        user_logined = request.user
        app_user = get_object_or_404(AppUser, user = user_logined)
        files = File.objects.all().filter(user=app_user)        
        print(files)
        dict_files = {}
        index = 0
        for file in files:
            dict_files[index] = {'qr': file.qr,
                                'name':file.name,
                                'user': file.user.user.email,
                                'time_generated':str(file.time_generated),
                                'restarted':file.restarted,
                                'path':file.path,
                                'plan_to_send':str(file.plan_to_send),
                                'plan_to_send_generator': str(file.plan_to_send_generator),
                                'approved':file.approved,
                                'disapproved': file.disapproved}    
            index += 1
        dict_files_dump = json.dumps(dict_files)
        json_files = json.loads(dict_files_dump)
        return Response(json_files, status=status.HTTP_200_OK)


class ListRole(APIView):
    authentication_classes = (TokenAuthentication,)
    permission_classes = (IsAuthenticated,)

    def get(self, request, *args, **kwargs):
        user_logined = request.user
        app_user = get_object_or_404(AppUser, user=user_logined)
        print(user_logined.first_name, '@@@@@', app_user.roles.all())
        roles_dict = {}     
        index = 0   
        for i in app_user.roles.all():
            roles_dict[index] ={'name': i.name, 'department': i.department} 
            index += 1

        dictRolesDump = json.dumps(roles_dict)
        jsonRoles = json.loads(dictRolesDump)
        
        return Response(jsonRoles, status=status.HTTP_200_OK)

class GetAllRoles(APIView):
    authentication_classes = (TokenAuthentication,)
    permission_classes = (IsAuthenticated,)
    def get(self, request, *args, **kwargs):
        roles_dict = {}     
        index = 0   
        for i in Role.objects.all():
            roles_dict[index] = {'name': i.name, 'department': i.department} 
            index += 1
        dictRolesDump = json.dumps(roles_dict)
        jsonRoles = json.loads(dictRolesDump)
        
        return Response(jsonRoles, status=status.HTTP_200_OK)

class ViewMyApprovedDisaaprovedFiles():
    pass

class ViewFilesApprovedDissaprovedByMe():
    pass

class FileDetail():
    pass

class ViewMyPlanToSendFiles():
    pass    

