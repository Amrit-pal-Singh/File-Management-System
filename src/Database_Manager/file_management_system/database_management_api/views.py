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

        if(is_officer == False):
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
        user = User.objects.create_user(email, email, password)
        user.first_name = self.serializer.validated_data['first_name']
        user.last_name = self.serializer.validated_data['last_name']
        user.email = email
        user.save()
        # except:
        #     return Response({'Error': 'User With This Credentials Cannot be Stored'}, status=status.HTTP_400_BAD_REQUEST)
        return Response(self.serializer.validated_data, status=status.HTTP_201_CREATED)


class ListUsers(APIView):
    # authentication_classes = (TokenAuthentication, )
    # permission_classes = (IsAdminUser, IsAuthenticated)
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
    
