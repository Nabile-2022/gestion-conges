import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Absence } from 'src/app/models/absence';
import { StatutAbsence } from 'src/app/models/statut-absence';
import { TypeAbsence } from 'src/app/models/type-absence';
import { typeAbsenceLabels } from 'src/app/localisation/french';

@Component({
  selector: 'app-demande-absence',
  templateUrl: './demande-absence.component.html',
  styleUrls: ['./demande-absence.component.css']
})

export class DemandeAbsenceComponent implements OnInit
{
  form: FormGroup;
  absence!: Absence;
  typeAbsenceLabelsArray = Array.from(typeAbsenceLabels.values());

  constructor(private formBuilder: FormBuilder)
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
}
