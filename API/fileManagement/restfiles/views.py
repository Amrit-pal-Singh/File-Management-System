from django.shortcuts import render

from rest_framework.views import APIView

from rest_framework.generics import (
	RetrieveUpdateAPIView, 
	DestroyAPIView,
	ListAPIView,
	CreateAPIView
	)

from django.http import JsonResponse

from .serializers import listUserSerializer, listMyFilesSerializer
from .serializers import deleteSerializer
from .serializers import updateSerializer, addSerializer

from rest_framework.response import Response

from files.models import filesModel

from rest_framework.permissions import (
	IsAuthenticated,
	IsAuthenticatedOrReadOnly,
	)

from .permissions import IsOwnerOrReadOnly



from rest_framework.authentication import TokenAuthentication
from rest_framework.permissions import IsAuthenticated
from rest_framework.authentication import SessionAuthentication


class AddFile(CreateAPIView):
	# authentication_classes = (TokenAuthentication, SessionAuthentication)
	# permission_classes = (IsAuthenticated,)
	serializer_class = addSerializer
	queryset = filesModel.objects.all()

	def perform_create(self, serializer):
		serializer.save(username=self.request.user.username)


class ListAll(ListAPIView):
	# authentication_classes = (TokenAuthentication, SessionAuthentication)
	# permission_classes = (IsAuthenticated, )
	serializer_class = listUserSerializer

	def get_queryset(self, *args, **kwargs):
		return filesModel.objects.all()

class ListMyFiles(ListAPIView):
	# authentication_classes = (TokenAuthentication, SessionAuthentication)
	# permission_classes = (IsAuthenticated, IsOwnerOrReadOnly)
	serializer_class = listMyFilesSerializer
	
	def get_queryset(self, *args, **kwargs):
		return filesModel.objects.all().filter(username=self.request.user.username)

class DeletePost(DestroyAPIView):
	queryset = filesModel.objects.all()
	serializer_class = deleteSerializer
	lookup_field = 'qr'
	# permission_classes = [IsAuthenticatedOrReadOnly, IsOwnerOrReadOnly]
	# authentication_classes = (TokenAuthentication, SessionAuthentication)


class PostUpdateAPIView(RetrieveUpdateAPIView):
	queryset = filesModel.objects.all()
	serializer_class = updateSerializer
	lookup_field = 'qr'
	# permission_classes = [IsAuthenticatedOrReadOnly, IsOwnerOrReadOnly]
	# authentication_classes = (TokenAuthentication, SessionAuthentication)
	