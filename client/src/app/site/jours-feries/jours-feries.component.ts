import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { typeJourFerieLabels } from 'src/app/localisation/french';
import { JourFerie } from 'src/app/models/jour-ferie';
import { Role } from 'src/app/models/role';
import { StatutAbsence } from 'src/app/models/statut-absence';
import { TypeJourFerie } from 'src/app/models/type-jour-ferie';
import { JoursFeriesService } from 'src/app/services/jours-feries.service';
import { UserService } from 'src/app/services/user.service';
import { DeleteModalComponent } from './delete-modal/delete-modal.component';


@Component({
  selector: 'app-jours-feries',
  templateUrl: './jours-feries.component.html',
  styleUrls: ['./jours-feries.component.css']
})
export class JoursFeriesComponent implements OnInit {

  jourFeries !: JourFerie[];
  typeJourFerieLabels = typeJourFerieLabels;
  filteredJoursFeries !: JourFerie[];
  selectedYear !: any;

  constructor(private jourFerieService: JoursFeriesService,
    private userService: UserService,
    private modalService: NgbModal) { }

  ngOnInit(): void {
    this.jourFerieService.list().subscribe(jourFeries => {
      this.jourFeries = jourFeries;
      this.selectedYear = new Date().getFullYear().toString();
      this.filterJourFeries(this.selectedYear);
    });
  }

  getYears(): number[] {
    return [...new Set(this.jourFeries.map(jour => jour.date.getFullYear()).sort((a, b) => a - b)).add(new Date().getFullYear()).add(new Date().getFullYear()+1)];
  }

  filterJourFeries(selectedYear: string) {
    this.filteredJoursFeries = this.jourFeries.filter(jourFerie => jourFerie.date.getFullYear() === Number(selectedYear));
  }

  isAdminUser(): boolean {
    return this.userService.user?.role === Role.Administrateur;
  }

  isEditable(jourFerie: JourFerie): boolean {
    return (jourFerie.statut === StatutAbsence.Initiale) || (jourFerie.type === TypeJourFerie.Ferie);
  }

  edit(jourFerie: JourFerie): void {

  }

  delete(jourFerie: JourFerie): void {
    const modal = this.modalService.open(DeleteModalComponent);
    const modalComponent = modal.componentInstance as DeleteModalComponent;
    modalComponent.jourferie = jourFerie;

    modal.result.then(confirmed => {
      if (confirmed)
        this.jourFerieService.delete(jourFerie).subscribe(() => {
          this.jourFeries.splice(this.jourFeries.indexOf(jourFerie), 1);
          this.filterJourFeries(this.selectedYear);
        });
    });
  }

}
