# Generated by Django 2.2.1 on 2019-05-18 13:42

from django.conf import settings
from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    initial = True

    dependencies = [
        migrations.swappable_dependency(settings.AUTH_USER_MODEL),
    ]

    operations = [
        migrations.CreateModel(
            name='Prefer',
            fields=[
                ('id', models.AutoField(primary_key=True, serialize=False)),
                ('name', models.CharField(max_length=20, unique=True)),
                ('image', models.ImageField(default='dafault/default_image.png', upload_to='images/prefer/')),
            ],
            options={
                'verbose_name': 'prefer',
                'verbose_name_plural': 'prefers',
                'db_table': 'prefers',
            },
        ),
        migrations.CreateModel(
            name='UserPrefer',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('prefer', models.OneToOneField(on_delete=django.db.models.deletion.CASCADE, to='api.Prefer')),
                ('user', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, related_name='prefer', to=settings.AUTH_USER_MODEL)),
            ],
        ),
    ]
