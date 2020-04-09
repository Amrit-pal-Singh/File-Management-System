from rest_framework.views import APIView
from django.contrib.auth.models import User
from django.http import JsonResponse
from databaseManagement.models import (
    Hierarchy,
    Files,
    UserAccount,
    Office,
    OfficerAccount,
    Position,
)
from rest_framework.permissions import (
	IsAuthenticated,
	IsAuthenticatedOrReadOnly,
)
from .serializers import ( 
    AddUserAccountSerializer,
    AddHierarchySerializer,
    AddOfficerAccountSerializer,
    AddOfficeSerializer,
    AddFileSerializer,
    AddPositionSerializer,
)
from rest_framework import viewsets, mixins, status, generics
from rest_framework.authentication import TokenAuthentication
from rest_framework.authentication import SessionAuthentication
from fileManagement import settings
from rest_framework.generics import (CreateAPIView, RetrieveUpdateAPIView, UpdateAPIView, DestroyAPIView, ListAPIView, RetrieveAPIView)
from django.contrib.auth.hashers import make_password
import json
from rest_framework.permissions import IsAdminUser
from rest_framework.authtoken.views import ObtainAuthToken
from rest_framework.authtoken.models import Token
from rest_framework.response import Response
from django.shortcuts import get_object_or_404


class CustomAuthToken(ObtainAuthToken):
    def post(self, request, *args, **kwargs):
        response = super(CustomAuthToken, self).post(request, *args, **kwargs)
        token = Token.objects.get(key=response.data['token'])
        user = get_object_or_404(User, email=token.user.email)
        account = get_object_or_404(UserAccount, user=user)
        is_officer = False
        try:
            if(OfficerAccount.objects.all().filter(user=account)):
                is_officer = True            
        except:
            is_officer = False

        # user = dbModels.User.objects.all().filter(email=token.user.email)[0]
        first_name = user.first_name
        last_name = user.last_name
        
        if(is_officer == False):
            return Response({'token': token.key,
                         'email_address': token.user.email,
                         'admin_permissions': token.user.is_staff,
                         'first_name': first_name,
                         'last_name': last_name,
                         'address': account.address,
                         'phone_number': account.phone_number,
                         'is_officer': is_officer
            })
        officer = OfficerAccount.objects.all().filter(user=account)[0]
        return Response({'token': token.key,
                    'email_address': token.user.email,
                    'admin_permissions': token.user.is_staff,
                    'first_name': first_name,
                    'last_name': last_name,
                    'address': account.address,
                    'phone_number': account.phone_number,
                    'is_officer': is_officer,
                    'office': officer.office.name,
                    'position': officer.position,
        })
            

class AddUserAccount(mixins.CreateModelMixin,
                viewsets.GenericViewSet):
    serializer_class = AddUserAccountSerializer
    queryset = UserAccount.objects.all()
    def create(self, request, *args, **kwargs):
        self.serializer = self.get_serializer(data=request.data)
        self.serializer.is_valid(raise_exception=True)
        # try:
        email_address = self.serializer.validated_data['email_address']
        password = self.serializer.validated_data['password']
        self.serializer.validated_data['password'] = make_password(self.serializer.validated_data['password'])
        password = self.serializer.validated_data['password']
        # username, email_address, password
        user = User.objects.create_user(email_address, email_address, password)
        user.first_name = self.serializer.validated_data['first_name']
        user.last_name = self.serializer.validated_data['last_name']
        user.save()
        address = self.serializer.validated_data['address']
        phone_number = self.serializer.validated_data['phone_number']
        UserAccount.objects.create(user=user, address=address, phone_number=phone_number)
        # except:
        #     return Response({'Error': 'User With This Credentials Cannot be Stored'}, status=status.HTTP_400_BAD_REQUEST)
        return Response(self.serializer.validated_data, status=status.HTTP_201_CREATED)

class AddOfficerAccount(mixins.CreateModelMixin, 
                viewsets.GenericViewSet):
    # take user as input
    serializer_class = AddOfficerAccountSerializer
    queryset = OfficerAccount.objects.all()
    def create(self, request, *args, **kwargs):
        self.serializer = self.get_serializer(data=request.data)
        self.serializer.is_valid(raise_exception=True)
        # try:
        email_address = self.serializer.validated_data['email_address']
        user = get_object_or_404(User, username=email_address)
        account = get_object_or_404(UserAccount, user=user)
        position_name = self.serializer.validated_data['position']
        position = get_object_or_404(Position, name=position_name)
        office_id = self.serializer.validated_data['office_id']
        office = get_object_or_404(Office, office_id=office_id)
        OfficerAccount.objects.create(user=account, position=position, office=office)
        # except:
        #     return Response({'Error': 'User With This Credentials Cannot be Stored'}, status=status.HTTP_400_BAD_REQUEST)
        return Response(self.serializer.validated_data, status=status.HTTP_201_CREATED)



class AddOffice(mixins.CreateModelMixin,
                viewsets.GenericViewSet):
    queryset = Office.objects.all()
    serializer_class = AddOfficeSerializer
    def create(self, request, *args, **kwargs):
        self.serializer = self.get_serializer(data=request.data)
        self.serializer.is_valid(raise_exception=True)
        name = self.serializer.validated_data['name']
        office_id = self.serializer.validated_data['office_id']
        # hierarchy_ids = self.serializer.validated_data['hierarchy_ids']
        # hierarchy_not_fount = {}
        # for hierarchy_id in hierarchy_ids:
        #     if not Hierarchy.objects.all().filter(hierarchy_id=hierarchy_id):
        #         hierarchy_not_fount[hierarchy_id] = 'Not Found'
        
        # if bool(hierarchy_not_fount):
        #     return Response(hierarchy_not_fount, status=status.HTTP_400_BAD_REQUEST)
        
        office = Office.objects.create(name=name, office_id=office_id)
        # for hierarchy_id in hierarchy_ids:
        #     hierarchy = Hierarchy.objects.all().filter(hierarchy_id=hierarchy_id)[0]
        #     # hierarchy = get_object_or_404(Hierarchy, hierarchy_id=hierarchy_id)
        #     office.hierarchies.add(hierarchy)
        return Response(self.serializer.validated_data, status=status.HTTP_201_CREATED)

        


class AddHierarchy(mixins.CreateModelMixin,
                viewsets.GenericViewSet):
    serializer_class = AddHierarchySerializer
    queryset = Hierarchy.objects.all()
    def create(self, request, *args, **kwargs):
        self.serializer = self.get_serializer(data=request.data)
        self.serializer.is_valid(raise_exception=True)
        name = self.serializer.validated_data['name']
        hierarchy_id = self.serializer.validated_data['hierarchy_id']
        levels_string = self.serializer.validated_data['levels'].lower()
        # levels_list = levels_string.split(',')
        Hierarchy.objects.create(name=name, hierarchy_id=hierarchy_id, levels=levels_string)
        
        return Response(self.serializer.data, status=status.HTTP_201_CREATED)


class AddFile(mixins.CreateModelMixin,
                viewsets.GenericViewSet):
    serializer_class = AddFileSerializer
    queryset = Files.objects.all()
    def create(self, request, *args, **kwargs):
        self.serializer = self.get_serializer(data=request.data)
        self.serializer.is_valid(raise_exception=True)
        qr = self.serializer.validated_data['qr']
        email_address = self.serializer.validated_data['email_address']
        office_id = self.serializer.validated_data['office_id']
        # hierarchy_id = self.serializer.validated_data['hierarchy']
        user = get_object_or_404(User, username=email_address)
        userAccount = get_object_or_404(UserAccount, user=user)
        office = get_object_or_404(Office, office_id=office_id)
        # Hierarchy =get_object_or_404(Hierarchy, hierarchy=hierarchy_id)
        # Files.objects.create(qr=qr, user=userAccount, office=office, hierarchy=hierarchy_id)
        Files.objects.create(qr=qr, user=userAccount, office=office)
        return Response(self.serializer.data, status=status.HTTP_201_CREATED)

class AddPosition(mixins.CreateModelMixin,
                viewsets.GenericViewSet):
    serializer_class = AddPositionSerializer
    queryset = Position.objects.all()
    def create(self, request, *args, **kwargs):
        self.serializer = self.get_serializer(data=request.data)
        self.serializer.is_valid(raise_exception=True)
        name = self.serializer.validated_data['name']
        office_id = self.serializer.validated_data['office_id']
        office = get_object_or_404(Office, office_id=office_id)
        Position.objects.create(name=name, office=office)
        return Response(self.serializer.data, status=status.HTTP_201_CREATED)


        
class ListPositionInOffice(APIView):
    queryset = Position.objects.all()
    def get(self, request, office_id, *args, **kwargs):
        office = get_object_or_404(Office, office_id=office_id)
        objects = Position.objects.all().filter(office=office)
        dictPosition = {}
        index = 1
        for obj in objects:
            dictPosition[index] = {
                'name': obj.name,
            }
            index+=1
        dictDump = json.dumps(dictPosition)
        jsonPos = json.loads(dictDump)
        # check what status to return
        return Response(jsonPos)
                


class ListOffice(APIView):
    queryset = Office.objects.all()
    def get(self, request, *args, **kwargs):
        objects = Office.objects.all()
        dictOffice = {}
        index = 1
        for obj in objects:
            dictOffice[index] = {
                'name': obj.name,
                'office_id': obj.office_id,
                # 'hierarchies': str([hier.name for hier  in obj.hierarchies.all()])
            }
            index+=1
        dictOfficeDump = json.dumps(dictOffice)
        jsonOffice = json.loads(dictOfficeDump)
        # check what status to return
        return Response(jsonOffice)
        

class ListHierarchyInOffice(APIView):
    queryset = Hierarchy.objects.all()
    def get(self, request, office_id, *args, **kwargs):
        office = get_object_or_404(Office, office_id=office_id)
        objects = office.hierarchies.all()
        dictHierarchy = {}
        index = 1
        for obj in objects:
            list_levels = obj.levels.split(',')
            dictHierarchy[index] = {
                'name': obj.name,
                'hierarchy_id': obj.hierarchy_id,
                'levels': str([level for level in list_levels])
            }
            index+=1
        dictHierarchyDump = json.dumps(dictHierarchy)
        jsonHierarchy = json.loads(dictHierarchyDump)
        # check what status to return
        return Response(jsonHierarchy)
        


class UpadteFile():
    pass

class ListFilesUser():
    # queryset = Files.objects.all()
    # def get(self, request, email_address, *args, **kwargs):
    #     user = get_object_or_404(User, email=email_address)
    #     account = get_object_or_404(UserAccount, user=user)
    #     filesUser = Files.objects.all().filter(Files, user=account)
    pass

class ListFilesOffice():
    pass