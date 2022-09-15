import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Absence } from 'src/app/models/absence';
import { StatutAbsence } from 'src/app/models/statut-absence';
import { TypeAbsence } from 'src/app/models/type-absence';
import { typeAbsenceLabels } from 'src/app/localisation/french';
import { AbsenceService } from 'src/app/services/absence.service';
import { ActivatedRoute, Router } from '@angular/router';

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

  constructor(private formBuilder: FormBuilder, private absenceService: AbsenceService, private router : Router, private route: ActivatedRoute)
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

  // TODO use validators for the inputs?

  addAbsence(): void {
    this.absence.dateDebut = this.form.controls['dateDebut'].value;
    this.absence.dateFin = this.form.controls['dateFin'].value;
    this.absence.motif = this.form.controls['motif'].value;
    this.absence.type = this.form.controls['type'].value;

    const nowDate = new Date();
    let isOk = this.absence.dateFin > this.absence.dateDebut;
    isOk = isOk && (new Date(this.absence.dateDebut) >= nowDate);
    isOk = isOk && (new Date(this.absence.dateFin) > nowDate);
    // TODO ajouter d'autres tests

    if(isOk) {
      this.absenceService.addAbsence(this.absence).subscribe(a => this.router.navigate(['..'], {relativeTo: this.route}));
    }

  }
}
