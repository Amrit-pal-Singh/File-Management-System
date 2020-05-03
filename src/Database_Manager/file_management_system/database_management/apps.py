from django.apps import AppConfig


class DatabaseManagementConfig(AppConfig):
    name = 'database_management'
    def ready(self):
        import database_management.signals

