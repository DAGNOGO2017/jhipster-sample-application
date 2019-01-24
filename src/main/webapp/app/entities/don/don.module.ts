import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
    DonComponent,
    DonDetailComponent,
    DonUpdateComponent,
    DonDeletePopupComponent,
    DonDeleteDialogComponent,
    donRoute,
    donPopupRoute
} from './';

const ENTITY_STATES = [...donRoute, ...donPopupRoute];

@NgModule({
    imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [DonComponent, DonDetailComponent, DonUpdateComponent, DonDeleteDialogComponent, DonDeletePopupComponent],
    entryComponents: [DonComponent, DonUpdateComponent, DonDeleteDialogComponent, DonDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationDonModule {}
