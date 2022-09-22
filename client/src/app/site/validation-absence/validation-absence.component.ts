import { Component, OnInit } from '@angular/core';
import { statutAbsenceLabels, typeAbsenceLabels } from 'src/app/localisation/french';
import { Absence } from 'src/app/models/absence';
import { Role } from 'src/app/models/role';
import { Salarie } from 'src/app/models/salarie';
import { StatutAbsence } from 'src/app/models/statut-absence';
import { TypeAbsence } from 'src/app/models/type-absence';

@Component({
  selector: 'app-validation-absence',
  templateUrl: './validation-absence.component.html',
  styleUrls: ['./validation-absence.component.css']
})
export class ValidationAbsenceComponent implements OnInit {
  salarie!:Salarie;
  typeAbsenceLabels = typeAbsenceLabels;
  statutAbsenceLabels = statutAbsenceLabels;
  constructor() {
    this.salarie={
          nom:"Nom",
          prenom:"Prenom",
          email:"",
          role:Role.Employee,
          absences:[
            {
              id:0,
              dateDebut : new Date(),
              dateFin:new Date(),
              motif:"test",
              type:TypeAbsence.CongeNonPaye,
              statut:StatutAbsence.EnAttente,

            }
          ]


    }
   }



  ngOnInit(): void {
  }


  confirm(absence:Absence){

  }

  reject(absence:Absence){

  }
}
