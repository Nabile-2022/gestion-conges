import { TypeJourFerie } from "./type-jour-ferie";

export interface JourFerie
{
  date: Date;
  libelle: string;
  type: TypeJourFerie;
}
