# Generated by Django 3.0.4 on 2020-03-27 22:12

from django.db import migrations, models


class Migration(migrations.Migration):

    initial = True

    dependencies = [
        ('files', '0002_delete_files'),
    ]

    operations = [
        migrations.CreateModel(
            name='filesModel',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('qr', models.CharField(default='0', max_length=400)),
                ('username', models.CharField(default='0', max_length=400)),
                ('status', models.CharField(default='0', max_length=400)),
                ('path', models.CharField(default='0', max_length=1000)),
                ('Office', models.CharField(default='0', max_length=400)),
            ],
        ),
    ]
