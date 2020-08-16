from django.urls import path
from django.conf.urls import include, url
from . import views
from rest_framework import routers
from rest_framework.authtoken import views as authViews

router = routers.DefaultRouter()
router.register(r'add_file', views.CreateFile, basename='add_file')

urlpatterns = [
    url(r'', include(router.urls)),
    path('login/', views.CustomAuthToken.as_view()),
    path('roles/', views.ListRole.as_view(), name='list_roles'),
    path('generated_files/', views.ViewMyGeneratedFiles.as_view(), name='list_generated_files'),
    path('receive_file/<slug:pk>/', views.RecieveFile.as_view(), name='receive_file'),
    path('approve_disapprove_file/<slug:pk>/', views.ApproveDissaprove.as_view(), name='receive_file'),
    path('plan_to_send/<slug:pk>/', views.PlanToSend.as_view(), name='receive_file'),
    path('get_all_roles/', views.GetAllRoles.as_view(), name='get_all_roles'),
]
