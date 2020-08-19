from rest_framework import serializers
from rest_framework.serializers import(
    ModelSerializer,
)
from django.contrib.auth.models import User
from database_management.models import(
    User,
    File,
    Role,
    AppUser,
)
from django.shortcuts import get_object_or_404

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
    class Meta:
        model = File
        fields = ('qr', 'name',)

class ReceiveFileSerializer(ModelSerializer):
    email = serializers.EmailField(write_only=True)
    role = serializers.CharField(max_length=100, write_only=True)
    department = serializers.CharField(max_length=100, write_only=True, allow_blank=True)
    class Meta:
        model = File
        fields = ('qr', 'email', 'role', 'department',)

    def update(self, instance, validated_data):
        original_path = instance.path
        user = get_object_or_404(User, email = validated_data.get('email'))
        first_name = user.first_name
        last_name = user.last_name


        path = '#'
        path += first_name + " " + last_name
        path += ','
        path += validated_data.get('role')
        path += ','
        path += validated_data.get('department')
        
        prev_path = '#'+instance.path.split('#')[-1]
        if(path != prev_path):
            instance.path = original_path + path
        
        instance.plan_to_send = None
        instance.plan_to_send_generator = None

        # instance.description = validated_data.get('description', instance.description)
        # instance.body = validated_data.get('body', instance.body)
        # instance.author_id = validated_data.get('author_id', instance.author_id)
        instance.save()
        return instance


class ApproveDisapproveSerializer(ModelSerializer):
    email = serializers.EmailField(write_only=True)
    approve = serializers.IntegerField(write_only=True)
    role = serializers.CharField(max_length=100, write_only=True)
    department = serializers.CharField(max_length=100, write_only=True, allow_blank=True)
    
    class Meta:
        model = File
        fields = ('qr', 'email', 'role', 'department', 'approve')

    def update(self, instance, validated_data):
        approved = validated_data.get('approve')
        original_path = instance.path
        user = get_object_or_404(User, email = validated_data.get('email'))
        first_name = user.first_name
        last_name = user.last_name


        path = '#'
        path += first_name + " " + last_name
        path += ','
        path += validated_data.get('role')
        path += ','
        path += validated_data.get('department') 
        instance.restarted = False
        prev_path = '#'+instance.path.split('#')[-1]
        if(path != prev_path):
            instance.path = original_path + path
        instance.plan_to_send = None
        instance.plan_to_send_generator = None
        prev_approve = '#'+instance.approved.split('#')[-1]
        prev_disapprove = '#'+instance.disapproved.split('#')[-1]
        print(prev_approve, path, "#WRWQRIUQWGIUUAAJSSKJ", prev_path == path)
        if(approved and (path != prev_approve)):
            instance.approved += path
        elif(prev_disapprove != path):
            instance.disapproved += path
        instance.save()
        return instance


class PlanToSendSerializer(ModelSerializer):
    role = serializers.CharField(max_length=100, write_only=True)
    department = serializers.CharField(max_length=100, write_only=True, allow_blank=True)
    sender_email = serializers.EmailField(write_only=True)
    class Meta:
        model = File
        fields = ('qr', 'role', 'department', 'sender_email')
    
    def update(self, instance, validated_data):
        role = get_object_or_404(Role, name=validated_data.get('role'), department=validated_data.get('department'))
        app_user = get_object_or_404(User, email=validated_data.get('sender_email'))
        instance.plan_to_send_generator = app_user
        instance.plan_to_send = role
        instance.save()
        return instance
