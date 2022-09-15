import { AbstractControl, ValidatorFn, Validators } from "@angular/forms";

export class FormValidators
{
  static pastDate = (date: Date): ValidatorFn => (control: AbstractControl) =>
  {
    if (new Date(control.value) < date)
    {
      return { limit: { valid: false } };
    };

    return null;
  };
}
