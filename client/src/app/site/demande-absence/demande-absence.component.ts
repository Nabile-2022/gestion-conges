import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Absence } from 'src/app/models/absence';
import { StatutAbsence } from 'src/app/models/statut-absence';
import { TypeAbsence } from 'src/app/models/type-absence';
import { typeAbsenceLabels } from 'src/app/localisation/french';
import { AbsenceService } from 'src/app/services/absence.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormValidators } from '../shared/form-validators';

@Component({
  selector: 'app-demande-absence',
  templateUrl: './demande-absence.component.html',
  styleUrls: ['./demande-absence.component.css']
})

export class DemandeAbsenceComponent implements OnInit
{
  form!: FormGroup;
  absence!: Absence;

  typeAbsences = Object.values(TypeAbsence).filter(k => isNaN(Number(k)));
  typeAbsenceLabels = typeAbsenceLabels;

  constructor(private formBuilder: FormBuilder, private absenceService: AbsenceService, private router: Router, private route: ActivatedRoute)
  {
    this.absence =
    {
      dateDebut: new Date(),
      dateFin: new Date(),
      motif: '',
      type: TypeAbsence.CongePaye,
      statut: StatutAbsence.Initiale
    };
  }

  ngOnInit(): void
  {
    this.form = this.formBuilder.group(
      {
        dateDebut: [this.absence.dateDebut, { validators: [FormValidators.pastDate(this.absence.dateDebut)] }],
        dateFin: [this.absence.dateFin, { validators: [FormValidators.pastDate(this.absence.dateFin)] }],

        type: this.absence.type,
        motif: this.absence.motif
      }
    );
  }

  addAbsence(): void
  {
    this.absence.dateDebut = this.form.controls['dateDebut'].value;
    this.absence.dateFin = this.form.controls['dateFin'].value;
    this.absence.motif = this.form.controls['motif'].value;
    this.absence.type = this.form.controls['type'].value;

    let isOk = this.absence.dateFin > this.absence.dateDebut;
    isOk = isOk && (this.absence.type == TypeAbsence.CongeNonPaye && this.absence.motif !== null && this.absence.motif.trim().length > 0);

    if (isOk)
    {
      this.absenceService.addAbsence(this.absence).subscribe(a => this.router.navigate(['..'], { relativeTo: this.route }));
    }
  }
}
