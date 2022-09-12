import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Absence } from 'src/app/models/absence';

@Component({
  selector: 'app-demande-absence',
  templateUrl: './demande-absence.component.html',
  styleUrls: ['./demande-absence.component.css']
})
export class DemandeAbsenceComponent implements OnInit {

  form: FormGroup;
  absence!: Absence;

  constructor(private formBuilder: FormBuilder) {
    //this.form = formBuilder.group()
    this.form = formBuilder.group(
      {
        startDate: "01/01/2022",
        endDate: "01/01/2022",
        typeConge: "Congé Payé",
        motif: ""
      }
    );
  }

  ngOnInit(): void {
  }

}
