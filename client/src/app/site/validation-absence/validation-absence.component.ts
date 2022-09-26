import { Component, OnInit } from '@angular/core';
import { statutAbsenceLabels, typeAbsenceLabels } from 'src/app/localisation/french';
import { Absence } from 'src/app/models/absence';
import { Role } from 'src/app/models/role';
import { Salarie } from 'src/app/models/salarie';
import { StatutAbsence } from 'src/app/models/statut-absence';
import { TypeAbsence } from 'src/app/models/type-absence';
import { AbsenceService } from 'src/app/services/absence.service';
import { ManagerService } from 'src/app/services/manager.service';

@Component({
  selector: 'app-validation-absence',
  templateUrl: './validation-absence.component.html',
  styleUrls: ['./validation-absence.component.css']
})
export class ValidationAbsenceComponent implements OnInit
{
  absences!: any[];
  typeAbsenceLabels = typeAbsenceLabels;
  error?: string;

  constructor(private managerService: ManagerService, private absenceService: AbsenceService) { }

  ngOnInit(): void
  {
    this.managerService.getSalaries().subscribe(salaries =>
    {
      const absences: any[] = [];
      salaries.forEach(salarie => salarie.absences.filter((a: any) => [StatutAbsence.EnAttente, StatutAbsence.Rejetee].includes(a.statut.libelle as StatutAbsence)).forEach(a => absences.push({ salarie: salarie, absence: a })));

      this.absences = absences;
    });
  }

  confirm(absence: Absence)
  {
    this.absenceService.validate(absence).subscribe(
      {
        next: a =>
        {
          this.absences.splice(this.absences.indexOf(absence), 1);
          this.error = undefined;
        },
        error: e => this.error = e.error.message
      }
    );
  }

  reject(absence: Absence)
  {
    this.absenceService.reject(absence).subscribe(
      {
        next: () =>
        {
          this.absences.splice(this.absences.indexOf(absence), 1);
          this.error = undefined;
        },
        error: e => this.error = e.error.message
      });
  }

  dateFromString(date: string): Date { return new Date(date); }
}
