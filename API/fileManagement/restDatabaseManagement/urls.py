from django.urls import path
from django.conf.urls import include, url
from . import views
from rest_framework import routers
from rest_framework.authtoken import views as authViews

router = routers.DefaultRouter()
router.register(r'add_user', views.AddUserAccount, basename='addUser')
router.register(r'add_officer_account', views.AddOfficerAccount, basename='addOfficerAccount')
router.register(r'add_hierarchy', views.AddHierarchy, basename='addHierarchy')
router.register(r'add_office', views.AddOffice, basename='addOffice')
router.register(r'add_file', views.AddFile, basename='addFiles')
router.register(r'add_position', views.AddPosition, basename='addFiles')

urlpatterns = [
    path(r'positions/<office_id>/', views.ListPositionInOffice.as_view(), name='positions'),
    url(r'^login/', views.CustomAuthToken.as_view()),
    path(r'list_hierarchies/<office_id>', views.ListHierarchyInOffice.as_view(), name='list_hierarchies'),
    path(r'list_position_in_office/<office_id>', views.ListPositionInOffice.as_view(), name='list_position'),
    path(r'list_office/', views.ListOffice.as_view(), name='list_office'),
    url(r'', include(router.urls)),
]
