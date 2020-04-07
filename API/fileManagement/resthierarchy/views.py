from django.shortcuts import render
from rest_framework.views import APIView
from django.http import HttpResponse
from django.http import JsonResponse

from .serializers import (
	addSerializer,
	listSerializer,
	listofficeSerializer,
	showSerializer,
	addOfficeSerializer
	)

from rest_framework.generics import (CreateAPIView, RetrieveUpdateAPIView, DestroyAPIView, ListAPIView, RetrieveAPIView)
from rest_framework.response import Response

from hierarchy.models import hierarchyModel
from hierarchy.models import Office


from rest_framework.permissions import (
	IsAuthenticated,
	)


from rest_framework.authentication import TokenAuthentication
from rest_framework.authentication import SessionAuthentication

class hierarchyListAPIView(ListAPIView):
	# authentication_classes = (TokenAuthentication, SessionAuthentication)
	# permission_classes = (IsAuthenticated,)
	serializer_class = listSerializer
	queryset = hierarchyModel.objects.all()

class Addhierarchy(CreateAPIView):
	# authentication_classes = (TokenAuthentication, SessionAuthentication)
	# permission_classes = (IsAuthenticated,)
	serializer_class = addSerializer
	queryset = hierarchyModel.objects.all()

	def perform_create(self, serializer):
		serializer.save()

class AddOfficehierarchy(CreateAPIView):
	# authentication_classes = (TokenAuthentication, SessionAuthentication)
	# permission_classes = (IsAuthenticated,)
	serializer_class = addOfficeSerializer
	queryset = Office.objects.all()

	def perform_create(self, serializer):
		serializer.save()

# class Showhierarchy(RetrieveAPIView):
# 	queryset = hierarchyModel.objects.all()
# 	serializer_class = showSerializer
# 	lookup_field = 'id'


class officeAllAPIView(ListAPIView):
	serializer_class = listofficeSerializer
	queryset = Office.objects.all()


class officeIdAPIView(ListAPIView):
	serializer_class = listSerializer

	def get_queryset(self):
		return hierarchyModel.objects.filter(office=self.kwargs['id'])