from rest_framework import serializers
from rest_framework.serializers import(
    ModelSerializer,
)
from django.contrib.auth.models import User
from database_management.models import(
    User,
    File,
    Role,
)

class AddUserSerializer(ModelSerializer):
    first_name = serializers.CharField(max_length=100, write_only=True)
    last_name = serializers.CharField(max_length=100,  write_only=True)
    email = serializers.EmailField(write_only=True)
    password = serializers.CharField(write_only=True, style={'input_type': 'password'})
    class Meta:
        model = User
        fields = ('first_name', 'last_name', 'email', 'password')


class AddRoleSerializer(ModelSerializer):
    class Meta:
        model = Role
        fields = ('name', 'department')


class AddFileSerializer(ModelSerializer):
    email = serializers.EmailField(write_only=True)
    class Meta:
        model = File
        fields = ('qr', 'name', 'email')

