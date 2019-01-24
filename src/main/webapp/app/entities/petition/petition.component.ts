import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPetition } from 'app/shared/model/petition.model';
import { AccountService } from 'app/core';
import { PetitionService } from './petition.service';

@Component({
    selector: 'jhi-petition',
    templateUrl: './petition.component.html'
})
export class PetitionComponent implements OnInit, OnDestroy {
    petitions: IPetition[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected petitionService: PetitionService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.petitionService.query().subscribe(
            (res: HttpResponse<IPetition[]>) => {
                this.petitions = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPetitions();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPetition) {
        return item.id;
    }

    registerChangeInPetitions() {
        this.eventSubscriber = this.eventManager.subscribe('petitionListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
