import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
    PetitionComponent,
    PetitionDetailComponent,
    PetitionUpdateComponent,
    PetitionDeletePopupComponent,
    PetitionDeleteDialogComponent,
    petitionRoute,
    petitionPopupRoute
} from './';

const ENTITY_STATES = [...petitionRoute, ...petitionPopupRoute];

@NgModule({
    imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PetitionComponent,
        PetitionDetailComponent,
        PetitionUpdateComponent,
        PetitionDeleteDialogComponent,
        PetitionDeletePopupComponent
    ],
    entryComponents: [PetitionComponent, PetitionUpdateComponent, PetitionDeleteDialogComponent, PetitionDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationPetitionModule {}
