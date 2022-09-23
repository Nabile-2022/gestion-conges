import { StatutAbsence } from "./statut-absence";
import { TypeJourFerie } from "./type-jour-ferie";

export interface JourFerie
{
  id: number;
  date: Date;
  libelle: string;
  type: TypeJourFerie;
  statut: StatutAbsence;
}
