from django.urls import path
from django.conf.urls import include, url
from . import views
from rest_framework import routers
from rest_framework.authtoken import views as authViews

router = routers.DefaultRouter()
router.register(r'add_user', views.AddUser, basename='add_user')

urlpatterns = [
    path(r'list_users/', views.ListUsers.as_view(), name='list_users'),
    url(r'', include(router.urls)),
    url(r'^login/', views.CustomAuthToken.as_view()),

]
