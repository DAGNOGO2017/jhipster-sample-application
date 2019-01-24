import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
    ActiviteComponent,
    ActiviteDetailComponent,
    ActiviteUpdateComponent,
    ActiviteDeletePopupComponent,
    ActiviteDeleteDialogComponent,
    activiteRoute,
    activitePopupRoute
} from './';

const ENTITY_STATES = [...activiteRoute, ...activitePopupRoute];

@NgModule({
    imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ActiviteComponent,
        ActiviteDetailComponent,
        ActiviteUpdateComponent,
        ActiviteDeleteDialogComponent,
        ActiviteDeletePopupComponent
    ],
    entryComponents: [ActiviteComponent, ActiviteUpdateComponent, ActiviteDeleteDialogComponent, ActiviteDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationActiviteModule {}
