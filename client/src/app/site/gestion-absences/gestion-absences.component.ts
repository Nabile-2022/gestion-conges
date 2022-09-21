import { Component, OnInit } from '@angular/core';
import { Absence } from 'src/app/models/absence';
import { CompteurAbsences } from 'src/app/models/compteur-absences';
import { AbsenceService } from 'src/app/services/absence.service';
import { StatutAbsence } from 'src/app/models/statut-absence';
import { typeAbsenceLabels, statutAbsenceLabels } from 'src/app/localisation/french';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-gestion-absences',
  templateUrl: './gestion-absences.component.html',
  styleUrls: ['./gestion-absences.component.css']
})
export class GestionAbsencesComponent implements OnInit
{
  absences!: Absence[];
  compteur!: CompteurAbsences;

  typeAbsenceLabels = typeAbsenceLabels;
  statutAbsenceLabels = statutAbsenceLabels;

  constructor(private absenceService: AbsenceService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void
  {
    this.absenceService.list().subscribe(absences => this.absences = absences);
    this.absenceService.getCompteur().subscribe(compteur => this.compteur = compteur);
  }

  isEditable(absence: Absence) { return [StatutAbsence.Initiale, StatutAbsence.Rejetee].includes(absence.statut); }

  edit(absence: Absence)
  {
    this.router.navigate(['modifier'], { relativeTo: this.route, state: { absence: absence } });
  }

  delete(absence: Absence)
  {
    this.absenceService.delete(absence).subscribe(() => this.absences.splice(this.absences.indexOf(absence), 1));
  }
}
