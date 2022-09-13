import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Absence } from 'src/app/models/absence';
import { StatutAbsence } from 'src/app/models/statut-absence';

@Component({
  selector: 'app-demande-absence',
  templateUrl: './demande-absence.component.html',
  styleUrls: ['./demande-absence.component.css']
})
export class DemandeAbsenceComponent implements OnInit {

  form: FormGroup;
  absence!: Absence;

  constructor(private formBuilder: FormBuilder) {
    this.form = formBuilder.group(
      {
        startdate: "01/01/2022",
        enddate: "01/01/2022",
        type: "Congé payé",
        motif: ""
      }
    );
    this.absence.statut = StatutAbsence.Initiale;
  }

  ngOnInit(): void {
  }

}
