import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IActivite } from 'app/shared/model/activite.model';

@Component({
    selector: 'jhi-activite-detail',
    templateUrl: './activite-detail.component.html'
})
export class ActiviteDetailComponent implements OnInit {
    activite: IActivite;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ activite }) => {
            this.activite = activite;
        });
    }

    previousState() {
        window.history.back();
    }
}
