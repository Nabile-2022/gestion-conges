import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { typeJourFerieLabels } from 'src/app/localisation/french';
import { JourFerie } from 'src/app/models/jour-ferie';



@Component({
  selector: 'app-delete-modal',
  templateUrl: './delete-modal.component.html',
  styleUrls: ['./delete-modal.component.css']
})
export class DeleteModalComponent
{
  jourferie!: JourFerie;
  typeJourFerieLabels = typeJourFerieLabels;

  constructor(private modal: NgbActiveModal) { }

  confirm()
  {
    this.modal.close(true);
  }

  cancel()
  {
    this.modal.close(false);
  }
}
