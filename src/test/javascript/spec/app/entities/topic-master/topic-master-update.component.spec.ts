import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { QpgTestModule } from '../../../test.module';
import { TopicMasterUpdateComponent } from 'app/entities/topic-master/topic-master-update.component';
import { TopicMasterService } from 'app/entities/topic-master/topic-master.service';
import { TopicMaster } from 'app/shared/model/topic-master.model';

describe('Component Tests', () => {
  describe('TopicMaster Management Update Component', () => {
    let comp: TopicMasterUpdateComponent;
    let fixture: ComponentFixture<TopicMasterUpdateComponent>;
    let service: TopicMasterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [QpgTestModule],
        declarations: [TopicMasterUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TopicMasterUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TopicMasterUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TopicMasterService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TopicMaster(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new TopicMaster();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
