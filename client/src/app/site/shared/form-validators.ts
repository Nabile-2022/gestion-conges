import { AbstractControl, ValidatorFn, Validators } from "@angular/forms";
import { Absence } from "src/app/models/absence";
import { TypeAbsence } from "src/app/models/type-absence";

export class FormValidators
{
  static pastDate = (date: Date): ValidatorFn => (control: AbstractControl) =>
  {
    if (new Date(control.value) <= date)
    {
      return { limit: { valid: false } };
    };

    return null;
  };

  static nonEmptyText = (absence: Absence): ValidatorFn => (control: AbstractControl) => absence.type !== TypeAbsence.CongeNonPaye || (control.value !== null && control.value.trim().length > 0) ? null : { valid: false };
}
