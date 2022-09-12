import { StatutAbsence } from "./statut-absence";
import { TypeAbsence } from "./type-absence";

export interface Absence
{
  dateDebut: Date;
  dateFin: Date;
  motif: string;
  type: TypeAbsence;
  statut: StatutAbsence;
}
