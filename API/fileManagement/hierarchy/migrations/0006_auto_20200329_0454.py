# Generated by Django 3.0.4 on 2020-03-29 04:54

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('hierarchy', '0005_auto_20200327_2329'),
    ]

    operations = [
        migrations.RenameField(
            model_name='hierarchymodel',
            old_name='office',
            new_name='officeId',
        ),
        migrations.AddField(
            model_name='hierarchymodel',
            name='Office',
            field=models.CharField(default=0, max_length=400),
        ),
    ]
