from rest_framework import serializers
from rest_framework.serializers import(
    ModelSerializer,
)
from django.contrib.auth.models import User
from databaseManagement.models import(
    UserAccount,
    OfficerAccount,
    Files,
    Hierarchy,
    Office,
    Position,
)


class AddUserAccountSerializer(ModelSerializer):
    '''
    Taking the info on signUp
    '''
    first_name = serializers.CharField(max_length=100, write_only=True)
    last_name = serializers.CharField(max_length=100,  write_only=True)
    email_address = serializers.EmailField(write_only=True)
    password = serializers.CharField(write_only=True,
        style={'input_type': 'password', 'placeholder': 'Password'}
    )
    class Meta:
        model = UserAccount
        fields = ('first_name', 'last_name', 'email_address', 'password', 'address', 'phone_number')

class AddOfficerAccountSerializer(ModelSerializer):
    '''
    If the user is Officer. 
    The happens after signUp
    '''
    email_address = serializers.EmailField(write_only=True)
    position = serializers.CharField(max_length=100, write_only=True)
    office_id = serializers.CharField(max_length=100, write_only=True)
    class Meta:
        model = OfficerAccount
        fields = ('email_address', 'position', 'office_id')


class AddHierarchySerializer(ModelSerializer):
    '''
    The level in which the file will process
    Other people can also scan the file in the middle 
    Their entries will also be stored in the file info.
    '''
    class Meta:
        model = Hierarchy
        fields = ('name', 'hierarchy_id', 'levels')

class AddHierarchyToOfficeSerializer(ModelSerializer):
    '''
    Add new hierarchies to The office.
    '''
    new_hierarchy_levels = serializers.CharField(max_length=100, write_only=True)
    new_hierarchy_id = serializers.CharField(max_length=100, write_only=True)
    class Meta:
        model = Office
        fields = ('name', 'code', 'new_hierarchy_id', 'new_hierarchy_levels')

class AddFileSerializer(ModelSerializer):
    '''
    When new file is applied by the user
    '''
    email_address = serializers.EmailField(write_only=True)
    office_id = serializers.CharField(max_length=100, write_only=True)
    class Meta:
        model = Files
        # fields = ('qr', 'email_address', 'office', 'hierarchy')
        fields = ('qr', 'email_address', 'office_id')


class UpdateFileSerializer(ModelSerializer):
    '''
    When the qr is scanned this serializer is used to update the path.
    If the scanner is next position in hierarchy, then the status will get updated
    If the sacnner is not the next position in the hierarchy then the status will
    not get updated but the path will record the entry. 
    '''
    scanner_email = serializers.EmailField(write_only=True)
    class Meta:
        model = Files
        fields = ('qr', 'scanner_email')


class AddOfficeSerializer(ModelSerializer):
    '''
    here hierarchies will take list of ids of hierarchies as input
    '''
    # hierarchy_ids = serializers.ListField(child=serializers.CharField(max_length=100, write_only=True), write_only=True)
    class Meta:
        model = Office
        # fields = ('name', 'office_id', 'hierarchy_ids')
        fields = ('name', 'office_id')


class AddPositionSerializer(ModelSerializer):
    office_id = serializers.CharField(max_length=100, write_only=True)
    class Meta:
        model = Position
        fields = ('name', 'office_id')
