import { Absence } from "./absence";
import { Role } from "./role";

export interface Salarie
{
  nom: string;
  prenom: string;
  email: string;
  role: Role;
  absences:Absence[];
}
