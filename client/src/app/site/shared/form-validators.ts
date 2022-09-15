import { AbstractControl, ValidatorFn, Validators } from "@angular/forms";

export class FormValidators
{
  static numberBetween = (min: number, max: number): ValidatorFn => (control: AbstractControl) =>
  {
    if (!Number.isInteger(control.value))
      return {
        integer: { valid: false }
      };

    if (control.value < min || control.value > max)
      return {
        limit: { valid: false }
      };

    return null;
  };

  static title = (): ValidatorFn[] => [Validators.minLength(1), Validators.maxLength(30), Validators.pattern('[A-Za-z0-9 ]+')];

  static pastDate = (date : Date) : ValidatorFn => (control: AbstractControl) => {
    if (new Date(control.value) > date) {
      return {
        limit : {valid : false}
      }
    };
    return null;
  }
}
