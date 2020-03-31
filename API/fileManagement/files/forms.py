from django import forms
from .models import filesModel


class ListForm(forms.ModelForm):
		
	class Meta:
		model = filesModel
		exclude = [""]

class UpdateForm(forms.ModelForm):
	class Meta:
		model = filesModel
		exclude = ["qr", 'username', 'officeName']
