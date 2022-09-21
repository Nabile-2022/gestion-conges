import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { typeAbsenceLabels } from 'src/app/localisation/french';
import { Absence } from 'src/app/models/absence';

@Component({
  selector: 'app-delete-modal',
  templateUrl: './delete-modal.component.html',
  styleUrls: ['./delete-modal.component.css']
})
export class DeleteModalComponent
{
  absence!: Absence;
  typeAbsenceLabels = typeAbsenceLabels;

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
