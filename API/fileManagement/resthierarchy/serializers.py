from rest_framework import serializers
from rest_framework.serializers import (
	ModelSerializer,
)
from hierarchy.models import hierarchyModel, Office
from hierarchy.models import Office


class listSerializer(ModelSerializer):
	class Meta:
		model = hierarchyModel
		fields = [
			'Office',
			'officeId',
			'levels',
		]


class addSerializer(ModelSerializer):
	class Meta:
		model = hierarchyModel
		fields = [
			'Office',
			'officeId',
			'levels',
		]
class addOfficeSerializer(ModelSerializer):
	class Meta:
		model = Office
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
		model = Office
		fields = [
			'id',
			'id_category',
			'name',
		]