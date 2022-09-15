import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Absence } from 'src/app/models/absence';
import { StatutAbsence } from 'src/app/models/statut-absence';
import { TypeAbsence } from 'src/app/models/type-absence';
import { typeAbsenceLabels } from 'src/app/localisation/french';
import { AbsenceService } from 'src/app/services/absence.service';

@Component({
  selector: 'app-demande-absence',
  templateUrl: './demande-absence.component.html',
  styleUrls: ['./demande-absence.component.css']
})

export class DemandeAbsenceComponent implements OnInit
{
  form: FormGroup;
  absence!: Absence;
  typeAbsences = Object.values(TypeAbsence).filter(k => isNaN(Number(k)));
  typeAbsenceLabels = typeAbsenceLabels;

  constructor(private formBuilder: FormBuilder, private absenceService: AbsenceService)
  {
    this.absence =
    {
      dateDebut: new Date(),
      dateFin: new Date(),
      motif: '',
      type: TypeAbsence.CongePaye,
      statut: StatutAbsence.Initiale
    };
    this.form = formBuilder.group(
      {
        dateDebut: this.absence.dateDebut,
        dateFin: this.absence.dateFin,
        type: this.absence.type,
        motif: this.absence.motif
      }
    );

  }

  ngOnInit(): void
  {
  }

  addAbsence(): void {
    this.absence.dateDebut = this.form.get('dateDebut')?.value;
    this.absence.dateFin = this.form.get('dateFin')?.value;
    this.absence.motif = this.form.get('motif')?.value;
    this.absence.type = this.form.get('type')?.value;
/*
    console.log("absence : ");
    console.log('date debut : ' + this.absence.dateDebut);
    console.log('date fin : ' + this.absence.dateFin);
    console.log('motif : ' + this.absence.motif);
    console.log('statut : ' + this.absence.statut);
    console.log('type : ' + this.absence.type);
*/
    this.absenceService.addAbsence(this.absence).subscribe(a => this.absence = a); // TODO modify to manage the request's return
  }
}
