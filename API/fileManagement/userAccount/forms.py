from django import forms
from django.contrib.auth.forms import UserCreationForm, UserChangeForm
from restaccount.models import DriverUser
from django.contrib.auth.admin import UserAdmin


class RegisterForm(UserCreationForm):
	class Meta:
		model = DriverUser
		fields = ('username','email', 'password1', 'password2')
	


# class CustomUserChangeForm(UserChangeForm):

#     class Meta:
#         model = DriverUser
#         fields = ('username', 'email', 'Hierarchy_Name', 'Hierarchy_Level')