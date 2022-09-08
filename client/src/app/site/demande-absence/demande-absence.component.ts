import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-demande-absence',
  templateUrl: './demande-absence.component.html',
  styleUrls: ['./demande-absence.component.css']
})
export class DemandeAbsenceComponent implements OnInit {

  form!: FormGroup;

  constructor(private formBuilder: FormBuilder)
  {
    //this.form = formBuilder.group()
 /*this.form = formBuilder.group(

  );*/
  }

  ngOnInit(): void {
  }

}
