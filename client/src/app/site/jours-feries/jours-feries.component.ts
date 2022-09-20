import { Component, OnInit } from '@angular/core';
import { typeJourFerieLabels } from 'src/app/localisation/french';
import { JourFerie } from 'src/app/models/jour-ferie';
import { JoursFeriesService } from 'src/app/services/jours-feries.service';


@Component({
  selector: 'app-jours-feries',
  templateUrl: './jours-feries.component.html',
  styleUrls: ['./jours-feries.component.css']
})
export class JoursFeriesComponent implements OnInit {

  jourFeries !: JourFerie[];
  typeJourFerieLabels = typeJourFerieLabels;

  constructor(private jourFerieService: JoursFeriesService) { }

  ngOnInit(): void {
    this.jourFerieService.list().subscribe(jourFeries => this.jourFeries = jourFeries);
  }

  getYears(): number[] {
    return [...new Set(this.jourFeries.map(jour => jour.date.getFullYear()).sort((a,b) => a - b))];
  }



}
