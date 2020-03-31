from django import forms
from hierarchy.models import hierarchyModel

class AddForm(forms.ModelForm):
	class Meta:
		model = hierarchyModel
		exclude = [""]
		
class ListForm(forms.ModelForm):
	class Meta:
		model = hierarchyModel
		exclude = [""]