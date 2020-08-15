# Generated by Django 3.0.8 on 2020-08-15 12:20

from django.conf import settings
from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('database_management', '0008_auto_20200815_1219'),
    ]

    operations = [
        migrations.AlterField(
            model_name='file',
            name='plan_to_send',
            field=models.ForeignKey(null=True, on_delete=django.db.models.deletion.CASCADE, to='database_management.Role'),
        ),
        migrations.AlterField(
            model_name='file',
            name='plan_to_send_generator',
            field=models.ForeignKey(null=True, on_delete=django.db.models.deletion.CASCADE, related_name='plan_to_send_generator_person', to=settings.AUTH_USER_MODEL),
        ),
    ]
