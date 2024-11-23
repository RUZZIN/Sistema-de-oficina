import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { importProvidersFrom } from '@angular/core';
import { appConfig } from './app/app.config';

bootstrapApplication(AppComponent, appConfig ).catch(err => console.error(err));
