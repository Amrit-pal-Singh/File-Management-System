from rest_framework import serializers
from rest_framework.serializers import (
	ModelSerializer,
)
from hierarchy.models import hierarchyModel, officeName
from hierarchy.models import officeName


class listSerializer(ModelSerializer):
	class Meta:
		model = hierarchyModel
		fields = [
			'officeName',
			'officeId',
			'levels',
		]


class addSerializer(ModelSerializer):
	class Meta:
		model = hierarchyModel
		fields = [
			'officeName',
			'officeId',
			'levels',
		]
class addOfficeSerializer(ModelSerializer):
	class Meta:
		model = officeName
		fields = [
			'id_category',
			'name'
		]

class showSerializer(ModelSerializer):
	class Meta:
		model = hierarchyModel
		fields = [
			'id',
			'office',
			'levels',
		]


class listofficeSerializer(ModelSerializer):
	class Meta:
		model = officeName
		fields = [
			'id',
			'id_category',
			'name',
		]