import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDon } from 'app/shared/model/don.model';

@Component({
    selector: 'jhi-don-detail',
    templateUrl: './don-detail.component.html'
})
export class DonDetailComponent implements OnInit {
    don: IDon;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ don }) => {
            this.don = don;
        });
    }

    previousState() {
        window.history.back();
    }
}
