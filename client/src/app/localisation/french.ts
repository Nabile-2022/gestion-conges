import { StatutAbsence } from "../models/statut-absence";
import { TypeAbsence } from "../models/type-absence";
import { TypeJourFerie } from "../models/type-jour-ferie";

export const typeAbsenceLabels = new Map<TypeAbsence, string>(
  [
    [TypeAbsence.CongeNonPaye, 'Congé non-payé'],
    [TypeAbsence.CongePaye, 'Congé payé'],
    [TypeAbsence.RTT, 'RTT'],
  ]
);
export const statutAbsenceLabels = new Map<StatutAbsence, string>(
  [
    [StatutAbsence.EnAttente, 'En attente'],
    [StatutAbsence.Initiale, 'Initiale'],
    [StatutAbsence.Rejetee, 'Rejetée'],
    [StatutAbsence.Validee, 'Validée'],
  ]
);
export const typeJourFerieLabels = new Map<TypeJourFerie, string>(
  [
    [TypeJourFerie.Ferie, 'Férié'],
    [TypeJourFerie.RTT, 'RTT'],
  ]
);
