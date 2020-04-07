from rest_framework import serializers
from rest_framework.serializers import (
	ModelSerializer,
)
from files.models import filesModel

class listUserSerializer(ModelSerializer):
	class Meta:
		model = filesModel
		fields = [
			'qr',
			'status',
			'path',
			'Office'
		]

class listMyFilesSerializer(ModelSerializer):
	class Meta:
		model = filesModel
		fields = [
			'qr',
			'status',
			'path',
			'Office'
		]

class addSerializer(ModelSerializer):
	class Meta:
		model = filesModel
		fields = [
			'qr',
			'Office'
		]

class deleteSerializer(ModelSerializer):
	class Meta:
		model = filesModel
		fields = [
			'qr',
		]


class updateSerializer(ModelSerializer):
	class Meta:
		model = filesModel
		fields = [
			'status',
			# 'path',
		]