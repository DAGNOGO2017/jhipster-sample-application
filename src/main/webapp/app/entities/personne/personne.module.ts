import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
    PersonneComponent,
    PersonneDetailComponent,
    PersonneUpdateComponent,
    PersonneDeletePopupComponent,
    PersonneDeleteDialogComponent,
    personneRoute,
    personnePopupRoute
} from './';

const ENTITY_STATES = [...personneRoute, ...personnePopupRoute];

@NgModule({
    imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PersonneComponent,
        PersonneDetailComponent,
        PersonneUpdateComponent,
        PersonneDeleteDialogComponent,
        PersonneDeletePopupComponent
    ],
    entryComponents: [PersonneComponent, PersonneUpdateComponent, PersonneDeleteDialogComponent, PersonneDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationPersonneModule {}
