import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Absence } from 'src/app/models/absence';
import { StatutAbsence } from 'src/app/models/statut-absence';
import { TypeAbsence } from 'src/app/models/type-absence';
import { typeAbsenceLabels } from 'src/app/localisation/french';
import { AbsenceService } from 'src/app/services/absence.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormValidators } from '../shared/form-validators';
import { Observable } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-demande-absence',
  templateUrl: './demande-absence.component.html',
  styleUrls: ['./demande-absence.component.css']
})

export class DemandeAbsenceComponent implements OnInit
{
  // TODO: switch on route path (submit = put|post, title...)
  form!: FormGroup;
  absence!: Absence;
  submitAction!: () => Observable<Absence>;
  title!: string;
  error?: string;

  typeAbsences = Object.values(TypeAbsence).filter(k => isNaN(Number(k)));
  typeAbsenceLabels = typeAbsenceLabels;

  constructor(private formBuilder: FormBuilder, private absenceService: AbsenceService, private router: Router, private route: ActivatedRoute)
  {
    route.url.subscribe(path =>
    {
      switch (path[path.length - 1].path)
      {
        case 'demande':
          this.submitAction = () => this.absenceService.addAbsence(this.absence);
          this.title = "Demande d'une absence";
          this.absence =
          {
            id: 0,
            dateDebut: this.addDaysToDate(new Date(), 1),
            dateFin: this.addDaysToDate(new Date(), 2),
            motif: '',
            type: TypeAbsence.CongePaye,
            statut: StatutAbsence.Initiale
          };
          break;
        case 'modifier':
          this.submitAction = () => this.absenceService.updateAbsence(this.absence);
          this.title = "Modification d'une absence";
          const nav = this.router.getCurrentNavigation();
          this.absence = nav?.extras && nav.extras.state && nav.extras.state['absence'];
          break;
      }
    });
  }

  ngOnInit(): void
  {
    this.form = this.formBuilder.group(
      {
        dateDebut: [this.dateToFormInputValue(this.absence.dateDebut), { validators: [FormValidators.pastDate(new Date())] }],
        dateFin: [this.dateToFormInputValue(this.absence.dateFin), { validators: [FormValidators.pastDate(new Date())] }],
        type: this.absence.type,
        motif: [this.absence.motif, { validators: [FormValidators.nonEmptyText(this.absence)] }]
      }
    );
  }

  submit(): void
  {
    this.form.controls['motif'].updateValueAndValidity();

    const datesValid = this.absence.dateFin > this.absence.dateDebut;

    if (!datesValid)
    {
      this.error = "La date de fin doit suivre la date de début.";
      return;
    }

    if (this.form.valid)
    {
      this.error = undefined;
      this.submitAction().subscribe(
        {
          next: a => this.router.navigate(['..'], { relativeTo: this.route }),
          error: (e: HttpErrorResponse) => this.error = e.error.message // If we were to stay on the page, we'd null this error field in the 'next' lambda.
        }
      );
    }
  }

  /**
   * The template needs to assign the form control value back to the component's variable, so it needs to go through this function.
   */
  dateFromISOFormat(date: string): Date { return new Date(date); }

  /**
   * Converts a date to the format that the HTML date input form control expects ('YYYY-MM-dd').
   */
  dateToFormInputValue(date: Date): string { return date.toISOString().split('T')[0]; }

  addDaysToDate(date: Date, days: number): Date
  {
    let newDate = new Date(date.valueOf());
    newDate.setDate(newDate.getDate() + days);
    return newDate;
  }
}
