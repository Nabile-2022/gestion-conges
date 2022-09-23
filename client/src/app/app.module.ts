import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { GestionAbsencesModule } from './site/gestion-absences/gestion-absences.module';
import { DemandeAbsenceModule } from './site/demande-absence/demande-absence.module';
import { HomeModule } from './site/home/home.module';
import { LoginModule } from './site/login/login.module';
import { SharedModule } from './site/shared/shared.module';
import { JoursFeriesModule } from './site/jours-feries/jours-feries.module';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ValidationAbsenceModule } from './site/validation-absence/validation-absence.module';


@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HomeModule,
    LoginModule,
    GestionAbsencesModule,
    DemandeAbsenceModule,
    JoursFeriesModule,
    SharedModule,
    HttpClientModule,
    NgbModule,
    ValidationAbsenceModule

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
